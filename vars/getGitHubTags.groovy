def call(String apiUrl) {
    //def apiUrl = "https://api.github.com/repos/your-username/your-repo/tags"
    def response = new URL(apiUrl).openConnection()
    response.setRequestProperty("Accept", "application/vnd.github.v3+json")
    def tags = new groovy.json.JsonSlurper().parse(response.inputStream)
    
    // Return tag names as a list, fallback to a static list if request fails
    return tags.collect { it.name } ?: ['v1.1.1', 'v2.2.2', 'v3.3.3']
}