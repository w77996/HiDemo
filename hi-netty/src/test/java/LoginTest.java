public class LoginTest {
    public static void main(String[] args) {
        String parentContent = null;
        Integer index = parentContent.indexOf(":");
        String sendContent = parentContent.substring(index + 1);
        System.out.println(sendContent);
    }
}
