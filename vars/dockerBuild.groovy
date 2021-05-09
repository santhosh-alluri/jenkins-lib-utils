void call(String repo, String tag, String dockerfile) {
  sh "docker build -t ${repo}:${tag} . -f ${dockerfile}"
}
