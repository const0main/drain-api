<?php
error_reporting(E_ERROR | E_PARSE);

$agent = $_GET['provider'];
$token = $_GET['token'];
$conn = mysqli_connect("localhost", "root", "", "drain");

$apiRequest = "";

if($agent == "drain") {
    if($token != null && $agent == "drain") {
        // authentication
        $rest = mysqli_query($conn, "SELECT * FROM `users` WHERE `token` = '$token'");
        $base -> status = "fail";
        if(mysqli_num_rows($rest) > 0) {
            $base->status = "success";
        }
        // get information
        $sql = "SELECT * FROM `users` WHERE `token`='$token'";
        if($result = $conn->query($sql)){
            foreach($result as $row){
                $base->uid = $row["uid"];
                $base->expire = $row["expire"];
                $base->role = $row["role"];
            }
        }
        // json return
        $apiRequest = json_encode($base);
        echo $apiRequest;
    }
}else {
    http_response_code(401);
}


?>