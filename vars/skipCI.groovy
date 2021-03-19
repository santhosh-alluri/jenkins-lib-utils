// Returns true if commit message contains [ci_skip] or [skip_ci]
boolean call() {
  // https://support.cloudbees.com/hc/en-us/articles/227266408-Current-Git-branch-is-HEAD-detached-at
  return sh(script: "git log -1 --pretty=%B | fgrep -ie '[skip_ci]' -e '[ci_skip]'", returnStatus: true) == 0
}
