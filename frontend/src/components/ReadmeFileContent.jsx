import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import ReadmeFile from "./ReadmeFile";

function ReadmeFileList(props) {
  const navigate = useNavigate();

  const Wrapper = styled.div`
      padding: 0;
      margin: 0 auto;
      width: calc(100% - 32px);
      display: flex;
      flex-direction: column;
      justify-content: center;
  `;

  return (
      <Wrapper>
        <div className="contentDiv">
          <h3> A.md </h3>
          <div>
              팀원 소개
              FE: 윤수영(L), 김다흰, 이동현
              BE: 김 현(VL), 강서의, 권유경
              Design: 정찬울
              트러블 슈팅
              1. Jenkins, Github Actions를 사용한 CI/CD 적용 (BackEnd)
              도입 이유
              코드 통합 및 배포 자동화를 통해 원활한 협업과 개발에만 집중할 수 있는 환경 제공
              문제 상황
              프로젝트 개발 단계에서 테스트 및 배포가 자주 일어나는데, 그럴 때마다 사람이 수동으로 하면 시간이 오래 걸리고 실수할 가능성이 있음
              테스트 또는 빌드가 실패했을 때 어느 시점부터 에러가 발생했는지 직접 찾아야 하는 불편함 발생
              해결 방안
              Jenkins
              GitHub Actions
              의견 조율
              Jenkins를 사용하기 위해선 별도의 서버 설치 및 설정이 필요하지만 다양한 플러그인과 많은 레퍼런스가 존재

          </div>
        </div>
      </Wrapper>
  );
}

export default ReadmeFileList;
