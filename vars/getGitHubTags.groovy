#!/usr/bin/env groovy

def fetchGitTags(String repoUrl) {
    // Fetch all remote tags from the repository
    def gitTags = []
    
    try {
        def tagsOutput = sh(script: "git ls-remote --tags ${repoUrl}", returnStdout: true).trim()
        gitTags = tagsOutput.readLines().collect { line ->
            line.split()[1].replaceAll('refs/tags/', '')
        }
    } catch (Exception e) {
        echo "Failed to fetch Git tags: ${e.message}"
    }

    return gitTags
}
