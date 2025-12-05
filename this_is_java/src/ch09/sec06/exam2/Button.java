package this_is_java.ch09.sec06.exam2;

public class Button {
    // 정적 멤버 인터페이스
    public static interface ClickListener {
        // 추상 메서드
        void onClick();
    }

    // 필드
    private ClickListener clickListener;

    // 메서드
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // 메서드
    public void click() {
        this.clickListener.onClick();
    }
}
