<?php
error_reporting(E_ERROR | E_PARSE);

function encryptData($plain_text, $key) {
    $cipher = "aes-256-cbc";
    $ivlen = openssl_cipher_iv_length($cipher);
    $iv = openssl_random_pseudo_bytes($ivlen);
    $ciphertext_raw = openssl_encrypt($plain_text, $cipher, $key, OPENSSL_RAW_DATA, $iv);
    $hmac = hash_hmac('sha256', $ciphertext_raw, $key, true);
    return base64_encode($iv.$hmac.$ciphertext_raw);
}

$keySecret = "12345678901234567890123456789012";
$agent = $_GET['provider'];
$token = $_GET['token'];
$hwid = $_GET['hwid'];
$conn = mysqli_connect("localhost", "root", "", "drain");

$apiRequest = "";

if($agent == "drain") {
    if($token != null && $agent == "drain") {
        // authentication
        $rest = mysqli_query($conn, "SELECT * FROM `users` WHERE `token` = '$token' AND `hwid` = '$hwid'");
        $base -> status = "fail";
        if(mysqli_num_rows($rest) > 0) {
            $base->status = "success";
        }
        // get information
        $sql = "SELECT * FROM `users` WHERE `token`='$token' AND `hwid` = '$hwid'";
        if($result = $conn->query($sql)){
            foreach($result as $row){
                $base->uid = $row["uid"];
                $base->expire = $row["expire"];
                $base->role = $row["role"];
                $userDate = strtotime(date('y-m-d'));
                $dbTime = strtotime($row["expire"]);
                $dbWith00 = "20" . date('y-m-d');
                //echo '<br> normal date user ' . $dbWith00;
                //echo '<br> normal date in db ' . $row['expire'];
                //echo '<br> db time - ' . $dbTime;
                //echo '<br> user time - ' . $userDate . "<br>";
                if($dbTime < $userDate){
                    $base -> status = "fail";
                }
            }
        }
        // json return
        $apiRequest = json_encode($base);
        echo encryptData($apiRequest, $keySecret);
    }
}else {
    http_response_code(401);
}


?>