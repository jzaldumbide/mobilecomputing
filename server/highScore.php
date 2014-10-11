<?php
$dbhost = 'localhost';
$dbuser = 'brickbreaker';
$dbpass = 'brickbreaker1';
$dbname = 'brickbreaker';
$conn = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
if( mysqli_connect_error() )
{
  die('Could not connect: ' . mysqli_connect_error());
}
//Retreive info
$action = isset($_REQUEST['action']) ? $_REQUEST['action']:"";
$name = isset($_REQUEST['name']) ? $_REQUEST['name']:"";
$score = isset($_REQUEST['score']) ? $_REQUEST['score']:"";

if ( $action == "add" && $name!="" && $score!="" ){
   $sql = "";
   $query = "INSERT INTO highScore (name, score) VALUES (?,?)";
   $statement = mysqli_prepare($conn,$query);

   //bind parameters for markers, where (s = string, i = integer, d = double,  b = blob)
    mysqli_stmt_bind_param($statement, "si", $name, $score);

   if(!mysqli_stmt_execute($statement)){
       die('Error inserting: ');
   }
   mysqli_stmt_close($statement);
}
else{
    $result = mysqli_query($conn,"SELECT name, score FROM highScore ORDER BY score DESC LIMIT 10");
    echo "<xml><highScores>";
    while($row = mysqli_fetch_array($result)) {
        echo "<highScore name=\"".$row['name'] . "\" score=\"" . $row['score'] . "\"/>";
    }
    echo "</highScores></xml>";
}
mysqli_close($conn);
?>

