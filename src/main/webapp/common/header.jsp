<%--
  Created by IntelliJ IDEA.
  User: leeyr
  Date: 2023/03/25
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

  <div class="d-flex flex-wrap justify-content-center container">
    <a
            href="/"
            class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none"
    >
<%--      <img src="../../../assets/img/logo.png" width="40" class="me-3" />--%>
      <img src="./assets/img/logo.png" width="40" class="me-3" />
      <span class="fs-4">Enjoy Trip</span>
    </a>

    <ul class="nav nav-pills">
      <li>
        <a href="/search/tourList.html" class="nav-link px-2 link-dark">관광지조회</a>
      </li>
      <li>
        <a href="#" class="nav-link px-2 link-dark">자유게시판</a>
      </li>
      <li>
        <a href="/article/notification.html" class="nav-link px-2 link-dark">공지사항</a>
      </li>
      <li class="nav-item">
        <a href="/account/login.html" class="nav-link link-dark px-2">로그인</a>
      </li>
      <li class="nav-item">
        <a href="/member/addMember.html" class="nav-link link-dark px-2">회원가입</a>
      </li>
    </ul>
  </div>