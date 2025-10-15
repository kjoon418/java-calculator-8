package calculator.model;

public interface CustomDelimiterManager {
    /**
     * 입력으로부터 커스텀 구분자를 추출하는 메서드. 커스텀 구분자가 존재하지 않을 시 null을 반환합니다.
     */
    String extractCustomDelimiter(String input);

    /**
     * 입력의 커스텀 구분자 선언부를 제거한 나머지 부분을 반환하는 메서드.
     */
    String stripCustomDelimiterDeclaration(String input);
}
