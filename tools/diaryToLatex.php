<?php

// check console
if (php_sapi_name() !== "cli") {
    echo "Run this script from console\n";
    die();
}

// options
$outputPath = false;
$csvPath = false;
$diarySeparator = '%GENERATOR:DIARY';

// read options from console switches
$scriptName = array_shift($argv); // drop script name

$shortOptions = "i:o:";
$options = getopt($shortOptions);

if (isset($options['h'])) {
    echo "Usage: php " . $scriptName . " -i input.csv -o output.csv\n";
    echo "Parses .csv diary file and inserts the generated latex content into the specified .tex file\n";
    die();
}

if (isset($options['i'])) {
    $csvPath = $options['i'];
}

if (isset($options['o'])) {
    $outputPath = $options['o'];
}

if ($outputPath === false || $csvPath === false) {
    echo "Please specify input (-i) and output (-o)\n";
    die();
}

// get csv from google docs
// TODO Reports 'You are logged out from your google account', why? The shared link requires no login.
/*$csvUrl = "**shared url of gdocs in csv format";
$csvPath = dirname(__FILE__) . '/diary.csv';


$csvHandle = fopen ($csvPath, 'w+'); // Download target
$ch = curl_init($csvUrl); // Download source

curl_setopt($ch, CURLOPT_TIMEOUT, 50);
curl_setopt($ch, CURLOPT_FILE, $csvHandle);
curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
curl_setopt($ch, CURLOPT_AUTOREFERER, true);
curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
curl_setopt($ch, CURLOPT_COOKIEFILE, "./cookie.txt");

curl_setopt($ch, CURLOPT_VERBOSE, true);

curl_exec($ch);
curl_close($ch);
fclose($csvHandle);*/

// open and parse csv
$rawLines = array();
if (($handle = fopen($csvPath, "r")) !== FALSE) {

    // drop header
    fgetcsv($handle, 0, ",");
    
    // read lines
    while (($line = fgetcsv($handle, 0, ",")) !== FALSE) {
        $rawLines[] = $line;
    }
    fclose($handle);
}

// generate diary latex source

$diaryOutput = "";
// table header
$diaryOutput .= 
"    \begin{center} 
        \begin{tabular}{| l | p{1.9cm} | p{2.6cm} | p{6.1cm} |}
            \hline
                Kezdet & Időtartam & Résztvevők & Leírás \\\
            \hline \hline \n";

foreach ($rawLines as $line) {
    $line[1] = $line[1] . ' óra';
    $diaryOutput .= implode(' & ', $line);
    $diaryOutput .= "\\\ \hline\n";
}
            
// table footer
$diaryOutput .= "
            \hline
        \end{tabular}
    \end{center}";
    
// write the latex diary into the output
$docInput = file_get_contents($outputPath);

$docParts = explode($diarySeparator, $docInput);

if (count($docParts) != 3) {
    echo "Failed to parse the output document. Please put the separator (".$diarySeparator.") to the beginning and ending of the place of the diary.\n";
    die();
}

// replace old diary
$docParts[1] = $diarySeparator ."\n". $diaryOutput ."\n". $diarySeparator;

$docOutput = implode("", $docParts);

file_put_contents($outputPath, $docOutput);
