package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.cucumber.java.sl.In;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.*;
import resources.ApiResources;
import settings.Utils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Steps {
    private RequestSpecification request;
    private Response response;
    private static String issueId;
    private static String commentId;

    @Given("I create jira issue with following data")
    public void i_create_a_jira_issue(DataTable table) throws FileNotFoundException {
        Map<String, String> map = table.asMap(String.class, String.class);
        AddIssuePayload addIssuePayload = new AddIssuePayload();

        Fields fields = new Fields();

        Project project = new Project();
        project.setKey(map.get("Project Key"));

        fields.setProject(project);
        fields.setSummary(map.get("Summary")+" "+ LocalDateTime.now());
        fields.setDescription(map.get("Description")+" "+System.currentTimeMillis());

        Issuetype issuetype = new Issuetype();
        issuetype.setName(map.get("Issue type"));

        fields.setIssuetype(issuetype);

        addIssuePayload.setFields(fields);

        request=given().spec(Utils.getRequestSpecification()).body(addIssuePayload)
                .header("Accept","application/json")
                .header("Content-Type","application/json");
    }

    @Given("I call {string} API with {string} http method")
    public void iCallApiWithHttpMethod(String resource, String method){
        ApiResources apiResources =ApiResources.valueOf(resource);
        switch (method){
            case "GET":
                response = request.when().get(apiResources.getResource());
                break;
            case "POST":
                response = request.when().post(apiResources.getResource());
                break;
            case "PUT":
                response = request.when().put(apiResources.getResource());
                break;
            case "DELETE":
                response = request.when().delete(apiResources.getResource());
                break;
            default:
                Assert.fail("Method not valid");
                break;
        }
    }

    @Given("I assign it to {string}")
    public void i_assign_it_to(String string) throws FileNotFoundException {
        issueId = Utils.getStringBody(response,"id");
        request=given().spec(Utils.getRequestSpecification()).pathParam("issueId",issueId)
                .body("{\n  \"accountId\": \"JIRAUSER10001\"\n}")
                .header("Accept","application/json")
                .header("Content-Type","application/json");
    }
    @When("I add following comment to previously created issue {string}")
    public void i_add_following_comment_to_previously_created_issue(String comment) throws FileNotFoundException {
        issueId = Utils.getStringBody(response,"id");
        request = given().spec(Utils.getRequestSpecification())
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .pathParam("issueId",issueId)
                .body(Payloads.addCommentPayload(comment));
    }
    @Then("The API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(),expectedStatus,"Status code not as expected.");
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("I add file {string} to issue")
    public void i_add_file_to_issue(String file) throws FileNotFoundException {
        issueId = Utils.getStringBody(response,"id");
        request = given().spec(Utils.getRequestSpecification())
                .header("X-Atlassian-Token","no-check")
                .header("Content-Type","multipart/form-data")
                .pathParam("issueId",issueId)
                .multiPart("file",new File("src/test/java/files/"+file));
    }

    @Given("I add file {string} to issue {string}")
    public void i_add_file_to_issue_id(String file, String issue) throws FileNotFoundException {
        issueId = issue;
        request = given().spec(Utils.getRequestSpecification())
                .header("X-Atlassian-Token","no-check")
                .header("Content-Type","multipart/form-data")
                .pathParam("issueId",issueId)
                .multiPart("file",new File("src/test/java/files/"+file));
    }

    @Then("I delete previously attachment added")
    public void i_delete_previously_attachment_added() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I validate comment is correctly added")
    public void i_validate_comment_is_correctly_added() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("I update my previous comment with following text {string}")
    public void i_update_my_previous_comment_with_following_text(String updateComment) throws FileNotFoundException {
        commentId = Utils.getStringBody(response,"id");
        request = given().spec(Utils.getRequestSpecification())
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .pathParam("issueId",issueId)
                .pathParam("commentId",commentId)
                .body(Payloads.addCommentPayload(updateComment));
        iCallApiWithHttpMethod("updateComment","PUT");
    }

    @Given("I want to delete issue {string}")
    public void deleteIssue(String issueId) throws FileNotFoundException {
        request = given().spec(Utils.getRequestSpecification())
                .header("Accept","application/json")
                .header("Content-Type","application/json")
                .pathParam("issueId",issueId);
    }

    @When("I create a request with bearer token")
    public void iCreateRequestBearerToken() throws FileNotFoundException {
        request = given().spec(Utils.getRequestSpecification());
        iCallApiWithHttpMethod("getToken", "POST");
        String accessToken = Utils.getStringBody(response, "access_token");
        request = given().spec(Utils.getRequestSpecification()).header("Authorization", "Bearer "+accessToken);
    }

    public static void main(String[] args) {

        System.out.println(ApiResources.valueOf("addIssue").getResource());

        ArrayList<String> myA = new ArrayList<>();
        myA.add("one");
        myA.add("two");

        for(int j=0; j<myA.size(); j++){
            System.out.println(myA.get(j));
        }

        String[] letters = {"a", "b"};
        for(int i=0; i<letters.length; i++ ){
            System.out.println(letters[i]);
        }

        //########IMPLEMENTS MAP INTERFACE########

        //Older since beginning
        //Safe and synchronized -------> Only one thread can access to methods
        //Not accept null keys and values
        Hashtable<String, Integer> myHt = new Hashtable<>();
        myHt.put("One", 1);
        myHt.put("Two", 2);
        //myHt.put(null, 3);
        System.out.println(myHt.entrySet());
        System.out.println(myHt.keySet());
        System.out.println(myHt.values());

        //Newer 1.2
        //thread not safe and non-synchronized
        //Faster
        //Single thread
        //Allows one null key
        //Allows multiple null values
        Map<String, Integer> myHas = new HashMap<>();
        myHas.put("One", 1);
        myHas.put("Two", 2);
        myHas.put(null, 3);
        myHas.put(null, 4);
        myHas.put("Five", null);
        myHas.put("Six", null);
        System.out.println(myHas.entrySet());
        System.out.println(myHas.keySet());
        System.out.println(myHas.values());
        for(Map.Entry<String, Integer> single:myHas.entrySet()){
            System.out.println(single.getKey() + " " + single.getValue());
        }

        //
        MyClass myClass = new MyClass();
        myClass.runThis();

        SecondClass secondClass = new SecondClass();
        secondClass.thisIsAbstract();
        secondClass.notAbstract();
        //Final and abstract are the opposite

        Set<Integer> mySet = new HashSet<>();
        mySet.add(3);
        mySet.add(2);
        mySet.add(3);
        System.out.println(mySet);
        //System.out.println(mySet.get(2)); --> Cannot get

        List<Integer> myArray = new ArrayList<>();
        myArray.add(3);
        myArray.add(2);
        myArray.add(3);
        System.out.println(myArray);
        System.out.println(myArray.get(2));

        //###########Balanced Parenthesis ##############
        System.out.println("Stack: "+isValid("(]"));
        //###########Reverse an integer##############
        /*int num = 123;
        int rev = 0;

        rev = (rev*10)+(num%10);//(0*10)+(3)=3
        num =  num/10;//(123/10)=12

        rev = (rev*10)+(num%10);//(3*10)+(2)=32
        num =  num/10;//(12/10)=1

        rev = (rev*10)+(num%10);//(32*10)+(1)=321
        num =  num/10;//(1/10)=0*/
        int rev=0;
        int num = 2002;
        while(num!=0){
            rev = rev*10 + num%10;
            num/=10;
        }
        System.out.println(rev);
        //###########Palindrome##############
        String original = "angna";
        String revString = "";
        StringBuilder sb2 = new StringBuilder();
        for(int i = original.length()-1; i>=0; i--){
            revString+=original.charAt(i);
            sb2.append(original.charAt(i));
        }

        if(original.replace(" ","").equalsIgnoreCase(revString.replace(" ",""))){
            System.out.println("PALINDROME!!!");
            System.out.println(sb2);
        }else {
            System.out.println("NOOOOT!!!");
        }
        //###########Matrix spiral##############
        /*
        * [
        *   [1,2,3]
        *   [4,5,6]
        *   [7,8,9]
        * ]
        * 1,2,3,6,9,8,7,4,5
        * */
        int[][] nums = {
                {1,2,3},
                //{4,5,6},
                //{7,8,9},
                {10,11,12}
        };
        System.out.println(spiralOrder(nums));
        //###########Count from 1 to 100 and write 'X' if the current number can be divided by 3 or 'Y' if by 5 and 'Z' if both##############
        for (int i = 1; i<=5; i++){
            if(i%15==0){
                System.out.println(i +" can be divided by 3 & 5 = Z");
            }else if(i%5==0){
                System.out.println(i +" can be divided by 5 = Y");
            }else if(i%3==0){
                System.out.println(i +" can be divided by 3 = X");
            }
        }
        //Write an algorithm to  find the Number of occurrences of char in the array
        String word = "Aaaaannngkneel";

        char letter = 'n';
        int found=0;
        for(int i=0; i<word.length();i++){
            if (word.toLowerCase().charAt(i)==letter){
                found++;
            }
        }
        System.out.println("The letter "+letter+" is preseted "+found+" time in string "+word);

        StringBuilder sb = new StringBuilder();
        int concurrence = 1;
        for (int i = 0; i<word.length();i++){
            String currentString = sb.toString();
            char currentChar = word.charAt(i);
            if (currentString.contains(String.valueOf(currentChar))){
                char lastLetter = currentString.charAt(sb.length()-2);
                if (lastLetter!=currentChar){
                    concurrence=1;
                    sb.append(currentChar).append(concurrence);
                }else{
                    concurrence++;
                    sb.replace(sb.toString().length()-1,sb.toString().length(),String.valueOf(concurrence));
                }
            }else {
                concurrence=1;
                sb.append(currentChar).append(concurrence);
            }
        }
        System.out.println(sb);
        //•	Find the nth consecutive occurence of a character in a given string.
        String validate = "AtoooZzz";
        int max_count = 0;
        int count = 0;
        char max_char=' ', pre_char=' ', current_char=' ';
        for(int i = 0; i<validate.length(); i++){
            current_char = validate.charAt(i);
            if(current_char==pre_char){
                count+=1;
            }else {
                count = 1;
            }

            if (count>max_count){
                max_count = count;
                max_char = current_char;
            }
            pre_char = current_char;
        }
        System.out.println("The most consecutive char is "+max_char);
        //•	write a program to find 2nd largest no
        int a[]={49,13,57,22,1,1,1,13,57,49,22};
        System.out.println("Second largest number in array is "+findSecond(a));
        //•	encontrar elementos repetidos en una lista
        Integer[] integerArray = {1,1,1,2,3,3,3,3,3,4};
        System.out.println(findDuplicates(Arrays.asList(integerArray)));
        //•	print only uppercase
        String str = "Welcome To Tutorials Point India";
        for(int i = 0; i < str.length(); i++) {
            if(Character.isUpperCase(str.charAt(i))) {
                System.out.println(str.charAt(i));
            }
        }
        //•	Find first repeated char
        System.out.println(findFirstCharDuplicated("hassa"));

    }

    public static char findFirstCharDuplicated(String word){
        Set<Character> duplicated = new HashSet<>();
        for (int i =0;i < word.length(); i++){
            if (!duplicated.add(word.charAt(i))){
                return word.charAt(i);
            }
        }
        return '0';
    }

    public static Set<Integer> findDuplicates(List<Integer> nums){
        Set<Integer> duplicates = new HashSet<>();
        Set<Integer> setToAvoidSingles = new HashSet<>();
        for (Integer num:nums){
            boolean flag = setToAvoidSingles.add(num);
            System.out.println(num + " "+flag);
            if (!flag){
                duplicates.add(num);
            }
        }
        System.out.println(setToAvoidSingles);
        return duplicates;
    }

    public static int findSecond(int[] a){
        Arrays.sort(a);
        for (int i = a.length-1; i>=0; i--){
            for (int j = a.length-2; j>=0; j--){
                if (a[i] > a[j]){
                    return a[j];
                }
            }
        }
        return 0;
    }

    public static boolean isValid(String str){
        Stack<Character> stk= new Stack<>();
        for(int i=0; i<str.length(); i++){
            char ch = str.charAt(i);
            if(ch=='('||ch=='{'||ch=='['){
                stk.push(ch);//{
            }else if(stk.empty()){
                return false;
            }else if(ch==')' && stk.pop()!='('){
                return false;
            }else if(ch=='}' && stk.pop()!='{'){
                return false;
            }else if(ch==']' && stk.pop()!='['){
                return false;
            }
        }
        // return true if no open parentheses left in stack
        return stk.empty();
    }

    public static List<Integer> spiralOrder(int[][] matrix){
        /*
        * [           [             [           [              [
        *   [1,2,3],     [4,5,6],       [4,5],       [4,5]       [5]
        *   [4,5,6],     [7,8,9],       [7,8]     ]            ]
        *   [7,8,9]   ]             ]
        * ]
        *   1,2,3       6,9             5,8         4             5
        *
        * 1,2,3,6,9,5,8,4,5
        */
        List<Integer> res = new ArrayList<>();
        int rowBegin=0;
        int rowEnd=matrix.length-1;
        int columnBegin=0;
        int columnEnd=matrix[0].length-1;

        while (rowBegin<=rowEnd&&columnBegin<=columnEnd){
            for (int i=columnBegin; i<=columnEnd; i++){
                res.add(matrix[rowBegin][i]);
            }
            rowBegin++;

            for (int i=rowBegin; i<=rowEnd; i++){
                res.add(matrix[i][columnEnd]);
            }
            columnEnd--;

            if (rowBegin<=rowEnd){
                for (int i=columnEnd; i>=columnBegin; i--){
                    res.add(matrix[rowEnd][i]);
                }
            }
            rowEnd--;

            if (columnBegin<=columnEnd){
                for (int i=rowEnd; i>=rowBegin; i--){
                    res.add(matrix[i][columnBegin]);
                }
            }
            columnBegin++;
        }

        return res;

    }

    public interface MyInterface{
        int number = 1;//By default is public static final
        void runThis();//By default is abstract
    }

    public abstract static class MyAbstract{
        public void notAbstract(){
            System.out.println("This is not abstract");
        }

        public abstract void thisIsAbstract();

        int second = 2;
        private int no;
    }

    public static class MyClass implements MyInterface{

        @Override
        public void runThis() {
            System.out.println("Runnning");
            System.out.println(number);
            //number=2;
        }
    }

    public static class SecondClass extends MyAbstract{

        @Override
        public void thisIsAbstract() {
            System.out.println("Abstract method");
            second = 2;
            System.out.println(second);
        }
    }
}

