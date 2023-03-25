package common.validation.validator.singup;

import common.validation.ValidationMessage;
import common.validation.dto.InvalidResponse;
import common.validation.validator.SignUpValidator;
import member.dto.MemberAddDto;

import java.util.Collections;
import java.util.List;

public class EmailValidator implements SignUpValidator {

    private static final InvalidResponse INVALID_RESPONSE = new InvalidResponse("email", ValidationMessage.EMAIL);
    private static final int MAX_LENGTH = 50;

    @Override
    public List<InvalidResponse> validate(MemberAddDto request) {
        String email = request.getEmail();

        if (email == null || isBlank(email)) {
            return Collections.singletonList(INVALID_RESPONSE);
        }

        if (isLength(email)) {
            return Collections.singletonList(INVALID_RESPONSE);
        }

        if (isEmail(email)) {
            return Collections.emptyList();
        }

        return Collections.singletonList(INVALID_RESPONSE);
    }

    private boolean isBlank(String email) {
        return email.trim().isEmpty();
    }

    private boolean isLength(String email) {
        return MAX_LENGTH < email.length();
    }

    private boolean isEmail(String email) {
        return email.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    }
}