/* Simple helper to check whether some endpoint returns a response with 200 status code */
boolean call(String endpoint, String expectedResponseCode = '200') {
  responseCode = sh(returnStdout: true, script: """curl -L -s -o /dev/null -w '%{http_code}' ${endpoint}""").trim()
  if (responseCode == expectedResponseCode) {
    echo "[INFO] responseCode: ${responseCode}"
    return true
  }
  echo "[INFO] responseCode: ${responseCode}"
  return false
}
