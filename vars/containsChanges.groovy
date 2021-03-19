boolean call(breakingChanges) {
  changedFiles = sh(script: 'git diff-tree --no-commit-id --name-only -r HEAD', returnStdout: true).trim()

  containsBreakingChange = false
  for (breakingChange in breakingChanges) {
    if (changedFiles.contains(breakingChange)) {
      containsBreakingChange = true
      println("[INFO] Change detected for ${breakingChange}")
    } else {
      println("[INFO] No change detected for ${breakingChange}")
    }
  }
  return containsBreakingChange
}
