package com.suwiki.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suwiki.data.db.entity.TimetableEntity
import com.suwiki.model.TimetableData

@Entity
data class TimetableEntity(
    @PrimaryKey var number: Long = 0, // 번호
    var major: String = "", // 개설학과
    var grade: String = "", // 개설학년
    var classNumber: String = "", // 교과목번호
    var classDivideNumber: String = "", // 분반
    var className: String = "", // 과목명
    var classification: String = "", // 이수 구분
    var professor: String = "", // 교수님 성함
    var time: String = "", // 장소,시간,요일 split ')' -> 장소 2군데 걸러줌  , ' ' -> 요일 2군데 걸러줌 ,
    // 교시를 다 읽어온 후 12345... 순서로 증가하는지 확인
    var credit: String = "",
)

fun TimetableData.toEntity(): TimetableEntity {
    return TimetableEntity(
        number,
        major,
        grade,
        classNumber,
        classDivideNumber,
        className,
        classification,
        professor,
        time,
        credit,
    )
}

fun TimetableEntity.toDomain(): TimetableData {
    return TimetableData(
        number,
        major,
        grade,
        classNumber,
        classDivideNumber,
        className,
        classification,
        professor,
        time,
        credit,
    )
}
