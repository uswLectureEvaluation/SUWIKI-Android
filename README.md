# Commit Message
**FIX** : 올바르지 않은 동작을 고친 후에 사용합니다.      
   
**ADD** : 코드, 테스트, 예제, 문서 등의 추가가 있을 때 사용합니다.      
   
**REMOVE** : 코드의 삭제가 있을 때 사용합니다.      
   
**IMPROVE** : 호환성, 테스트 커버리지, 성능, 검증 기능, 접근성 등 향상이 있을 때 사용합니다.    
   
**IMPLEMENT** : 구현체를 완성했을 때 사용합니다.      
   
**REVISE** : 문서의 개정이 있을 때 사용합니다.   
   
**MOVE** : 코드의 이동이 있을 때 사용합니다.   
   
**RENAME** : 이름 변경이 있을 때 사용합니다.
   
   

   # ETC
\app\src\main\res 위치에 keys.xml 파일을 추가해야합니다. keys.xml의 내용은 다음과 같습니다.
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="kakaoAdfitID">anything</string>
</resources>
```

google-services.json은 보안상의 이유로 추가하지 않았습니다.
