## Summary
<!-- 1–3 문장으로 무엇을 / 왜 -->

## 변경 사항
<!-- 영향받은 모듈/파일별로. 해당 없는 행은 삭제 -->

| Module | Change |
|---|---|
| `:` |  |

## 아키텍처 체크 (해당하는 항목만)
<!-- CLAUDE.md / MODULE_GRAPH.md 의 규칙 — 위반 없으면 체크, 무관하면 줄 삭제 -->

- [ ] `:datasource` 의존성 추가 시 소비자는 `:core:service` 한 곳뿐
- [ ] `:feature → :core:service` 직접 호출은 단순 read-only 만 (비즈니스 로직은 `:core:domain` UseCase 경유)
- [ ] `:core:model` 에 `android.*` import 없음 (pure Kotlin 유지)
- [ ] 새 라이브러리/플러그인 버전은 `gradle/libs.versions.toml` 경유 (모듈 build.gradle.kts 에 하드코딩 금지)
- [ ] 새 모듈 추가 시 필요한 convention plugin 만 적용 (`cws.android.hilt` 는 실제 DI 코드 있을 때만)

## Notes for reviewers
<!-- 의도적 트레이드오프, 후속 PR 예고, 알려진 한계, 리뷰어가 특히 봐줬으면 하는 부분 -->

## Test plan
- [ ] `./gradlew :app:assembleDebug` 통과
- [ ] (UI 변경 시) 디바이스/에뮬레이터에서 골든 패스 + 주요 엣지 동작 확인
- [ ] (로직 추가 시) 단위 테스트 추가 + `./gradlew :<module>:testDebugUnitTest` 통과
- [ ] (의존성 변경 시) `./gradlew build` 풀빌드 통과
