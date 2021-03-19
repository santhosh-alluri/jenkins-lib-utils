def call(sourceImage, targetImage) {
  sh "docker tag ${sourceImage} ${targetImage}"
}
