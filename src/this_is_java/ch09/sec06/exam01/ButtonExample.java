package this_is_java.ch09.sec06.exam01;

public class ButtonExample {
    public static void main(String[] args) {
        Button btnOk = new Button();

        class OkListener implements Button.ClickListener {
            @Override
            public void onClick() {
                System.out.println("Ok button click!!");
            }
        }

        btnOk.setClickListener(new OkListener());

        btnOk.click();

        Button btnCancel = new Button();

        class CancelListener implements Button.ClickListener {
            @Override
            public void onClick() {
                System.out.println("Cancel button click!!");
            }
        }

        btnCancel.setClickListener(new CancelListener());

        btnCancel.click();
    }
}
