# drain-api
Drain is a secure api provider. It takes data from a table using an encrypted key and a custom user agent to use the api. If the user agent is wrong, the api will return a 401 and close the connection. There is also a validity check and hiding exceptions.
And so is the response from the server asymmetrically encrypted by Open SSL and decrypted on the client side using a key. It also includes time subscriptions and binding by unique machine id (hwid)
