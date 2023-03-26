package member.service;

import common.exception.InformationChangeException;
import common.exception.SignUpException;
import member.Member;
import member.dto.MemberAddDto;
import member.repository.MemberJdbcRepository;
import member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static member.Authority.CLIENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceTest {

    private final MemberService memberService = MemberServiceImpl.getMemberService();
    private final MemberRepository memberRepository = MemberJdbcRepository.getMemberRepository();

    @BeforeEach
    void beforeEach() {
        memberRepository.save(new MemberAddDto("ssafy", "12345678", "김싸피", "ssafy@ssafy.com", "01012345678", "010101", "1", "광주5반", CLIENT));
        memberRepository.save(new MemberAddDto("ssafy", "12345678", "김싸피", "ssafy@ssafy.com", "01012345678", "010101", "1", "광주5반", CLIENT));
    }

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    @DisplayName("회원가입")
    void signUp() {
        //given
        MemberAddDto memberAddDto = new MemberAddDto("ssafy9", "12345678", "김싸피", "ssafy9@ssafy.com", "01011111111",  "010101", "1", "싸피9기광주5반", CLIENT);

        //when
        memberService.signUp(memberAddDto);

        //then
        Optional<Member> findMember = memberRepository.findByLoginId("ssafy");
        assertThat(findMember).isPresent();
    }

    @Test
    @DisplayName("아이디 중복 예외")
    void exception_loginId() {
        //given
        MemberAddDto memberAddDto = new MemberAddDto("ssafy", "12345678", "김싸피", "ssafy@ssafy.com", "01011111111", "010101", "1", "싸피9기광주5반", CLIENT);

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(memberAddDto))
                .isInstanceOf(SignUpException.class);
    }

    @Test
    @DisplayName("이메일 중복 예외")
    void exception_email() {
        //given
        MemberAddDto memberAddDto = new MemberAddDto("ssafy9", "12345678", "김싸피", "ssafy@ssafy.com", "01011111111", "010101", "1", "싸피9기광주5반", CLIENT);


        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(memberAddDto))
                .isInstanceOf(SignUpException.class);
    }

    @Test
    @DisplayName("연락처 중복 예외")
    void exception_phone() {
        //given
        MemberAddDto memberAddDto = new MemberAddDto("ssafy9", "12345678", "김싸피", "ssafy@ssafy.com", "01012345678", "010101", "1", "싸피9기광주5반", CLIENT);

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(memberAddDto))
                .isInstanceOf(SignUpException.class);
    }

    @Test
    @DisplayName("닉네임 중복 예외")
    void exception_nickname() {
        //given
        MemberAddDto memberAddDto = new MemberAddDto("ssafy9", "12345678", "김싸피", "ssafy@ssafy.com", "01011111111", "010101", "1", "광주5반", CLIENT);

        //when
        //then
        assertThatThrownBy(() -> memberService.signUp(memberAddDto))
                .isInstanceOf(SignUpException.class);
    }


    @Test
    @DisplayName("비밀번호 변경")
    void changeLoginPw() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        memberService.changePassword(member.getMemberId(), "pw123456");

        //then
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        assertThat(findMember.getLoginPw()).isEqualTo("pw123456");
    }

    @Test
    @DisplayName("비밀번호 변경#미등록 회원")
    void changeLoginPw_exception_member() {
        //given
        //when
        //then
        assertThatThrownBy(() -> memberService.changePassword(0L, "pw123456"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("이메일 변경")
    void changeEmail() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        memberService.changeEmail(member.getMemberId(), "ssafy9@ssafy.com");

        //then
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        assertThat(findMember.getEmail()).isEqualTo("ssafy9@ssafy.com");
    }

    @Test
    @DisplayName("이메일 변경#미등록 회원")
    void changeEmail_exception_member() {
        //given
        //when
        //then
        assertThatThrownBy(() -> memberService.changeEmail(0L, "ssafy9@ssafy.com"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("이메일 변경#이메일 중복")
    void changeEmail_exception_email() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        //then
        assertThatThrownBy(() -> memberService.changeEmail(member.getMemberId(), "ssafy@ssafy.com"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("연락처 변경")
    void changePhone() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        memberService.changePhone(member.getMemberId(), "01011111111");

        //then
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        assertThat(findMember.getPhone()).isEqualTo("01011111111");
    }

    @Test
    @DisplayName("연락처 변경#미등록 회원")
    void changePhone_exception_member() {
        //given
        //when
        //then
        assertThatThrownBy(() -> memberService.changePhone(0L, "01011111111"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("연락처 변경#연락처 중복")
    void changePhone_exception_phone() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        //then
        assertThatThrownBy(() -> memberService.changePhone(member.getMemberId(), "01012345678"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("닉네임 변경")
    void changeNickname() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        memberService.changeNickname(member.getMemberId(), "싸피9기광주5반");

        //then
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        assertThat(findMember.getNickname()).isEqualTo("싸피9기광주5반");
    }

    @Test
    @DisplayName("닉네임 변경#미등록 회원")
    void changeNickname_exception_member() {
        //given
        //when
        //then
        assertThatThrownBy(() -> memberService.changeNickname(0L, "싸피9기광주5반"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("닉네임 변경#닉네임 중복")
    void changeNickname_exception_nickname() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        //then
        assertThatThrownBy(() -> memberService.changeNickname(member.getMemberId(), "광주5반"))
                .isInstanceOf(InformationChangeException.class);
    }

    @Test
    @DisplayName("닉네임 변경#변경기간")
    void changeNickname_exception_term() {
        //given
        Member member = memberRepository.findByLoginId("ssafy").get();

        //when
        memberService.changeNickname(member.getMemberId(), "싸피9기광주5반");
        //then
        assertThatThrownBy(() -> memberService.changeNickname(member.getMemberId(), "싸피9기광주5반1"))
                .isInstanceOf(InformationChangeException.class);
    }
}