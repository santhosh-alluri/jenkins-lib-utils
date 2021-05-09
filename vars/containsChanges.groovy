/* Copyright (c) 2021, Irmantas Marozas
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. */

boolean call(List breakingChanges) {
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
