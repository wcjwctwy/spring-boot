public class Tests {
    public static void main(String[] args) {
        String a = String.class.getCanonicalName();
        switch (a){
            case "java.lang.String":
                System.out.println("是一个String");
                break;
            default:
                System.out.println("unknow");
                break;
        }
    }
}
