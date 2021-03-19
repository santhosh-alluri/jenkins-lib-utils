def call(images) {
  for (image in images) {
    sh "docker rmi -f \$(docker images --format \"{{.Repository}}:{{.Tag}}\" | grep ${image})"
  }
}
