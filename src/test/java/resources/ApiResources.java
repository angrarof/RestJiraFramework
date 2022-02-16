package resources;

public enum ApiResources {
    addIssue("/rest/api/2/issue")
    ,deleteIssue("/rest/api/2/issue/{issueId}")
    ,assignIssue("/rest/api/2/issue/{issueId}/assignee")
    ,addComment("/rest/api/2/issue/{issueId}/comment")
    ,updateComment("/rest/api/2/issue/{issueId}/comment/{commentId}")
    ,getComment("/rest/api/2/issue/{issueId}/comment/{commentId}")
    ,deleteComment("/rest/api/2/issue/{issueId}/comment/{commentId}")
    ,addAttachment("/rest/api/2/issue/{issueId}/attachments"),
    getToken("/token"),
    getDataWithToken("/data");

    private final String resource;

    ApiResources(String resource){
        this.resource = resource;
    }

    public String getResource(){
        return resource;
    }
}
