# 실무를 하면서... (2022 ver)
생각정리...

## 기획
* 명세된 기획 내용을 바탕으로 개발을 진행하기 때문에 이해를 하려고 해야한다.
  * 이해가 안된다면 지속적으로 물어보고 기획자의 생각과 구현하는 개발자의 생각을 지속적으로 맞추는 작업을 진행한다.
  * 회의을 진행하는 경우 아젠다가 명확할 수 있도록 하자. -> 회의를 통해서 참석자들이 납득될 수 있어야 한다.
* 기획서가 완벽할 수 없다. 기획에 필요하다고 생각되는 부분은 내가 의견을 개진한다.
  * 도메인 정책 / 개인정보 처리 등을 고려

## 설계
* 스키마 설계는 언제든 바뀔 수 있음을 인지한다.
* 프로젝트 내 패키지 설계 및 컴포넌트 설계는 팀내 룰이 존재하는 경우 거기에 따른다.
  * 팀내 룰이 없다면? -> 같이 협업하는 동료와 같이 맞춰나가고 이후에 프로젝트를 진행할 수 있도록 한다.
* 기획을 보고 어느 기술셋을 사용할지 모르는 경우가 있다.
  * 경험부족 및 배우지 않은 영역일 수 있다. -> 팀/협업자에게 공유하여 별도 개인공부를 해야한다.
* 복잡한 내용의 설계는 간단한 말로써 가볍게 설명되는 수준으로 시작한다. 이후에 조금씩 살을 붙인다.
  * 하고자 하는 설계의 내용을 잘게 쪼갠다.

## 업무 & 일정관리
* 좋은 업무툴/일정관리툴이 있더라도 사용하지 않는다면 소용없다.
* 하루에 한번 혹은 일주일에 한번이라도 팀 내에 어떤 일들을 하는지 공유되는 시간이 매우 필요하다.
  * 서로가 어떤 업무를 하고 있는지 인지하는게 중요하다.
* 업무의 공백이 없게 하기 위해선 내가 한 업무가 완료되면 팀원들에게 공유되는 시간이 필요하다.
* 내가 하려하는 업무를 리스트업시키며 내가 현재 어떤 업무를 하는지, 어떤 업무가 남았는지 스스로 관리할 수 있어야 한다.
  * 업무의 사이즈가 크면 더 잘게 나눌 수 있도록 한다.
* 작은 업무를 하더라도 기록에 남긴다.

## 문서화
* 운영팁이나 작업했던 내용들을 항상 문서화하고 팀 내에 공유할 수 있도록 한다.
  * 다음에 해당 작업은 다시 진행하는 경우 잘 작성된 문서가 있다면 별도의 설명 필요없이 그것만 있으면 되기 때문이다.

## 우리팀과 협업
* 좋은 팔로워십을 가지고 싶다.
  * 독단으로 행동하지 말 것
    * 팀장/파트장에 의해서 선 공유되고 이후의 프로세스는 팀원이 결정하지 않는다.
    * 내가 바로 쳐낼 수 있는 업무일지라도 바로 수행하지 않는다. -> 팀 내에 일감이 있다는 것을 인지시키는데 목적을 둔다.
  * 팀이 가고자 하는 방향과 내가 가고자 하는 방향의 싱크를 맞출 수 있도록 하고싶다.

## 타 팀과의 협업
* 당연한 이야기지만, 상대방의 업무도 일부 이해할 수 있어야 한다.

## 코드리뷰
* 코드리뷰문화는 툴에 상관없이 해당문화가 팀 내에 지속적으로 유지되도록 하는 것이 중요하다.
* 코드리뷰의 목적
  * 내가 작성한 비즈니스 로직을 리뷰원에게 설명할 때
  * 코드 작성하면서 발생할 사이드 이펙에 대한 더블체크가 필요할 때
  * 프로젝트 머지 전에 코딩 컨벤션등을 맞추고자 할 때
* 코드리뷰는 pr 템플릿을 만들어 내용의 형식을 일원화한다.
  * [pr template](https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository)

## 일하는 태도
* 실수를 하면 사과하고 다음에 그런일이 발생되지 않도록 노력한다.
* 감정적인 상황이 발생할 수 있다. 그 감정을 오랜기간 들고가려고 하지 않는다.
* 내가 진행하는 회의/설계/코딩/개념 등은 언제나 잘못될 수 있음을 인지한다.