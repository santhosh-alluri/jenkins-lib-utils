def call(repo, tag, dockerfile) {
  sh "docker build -t ${repo}:${tag} . -f ${dockerfile}"
}
