void call(String sourceImage, String targetImage) {
  sh "docker tag ${sourceImage} ${targetImage}"
}
