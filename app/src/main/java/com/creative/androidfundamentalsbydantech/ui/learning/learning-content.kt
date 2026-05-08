@file:JvmName("LearningContentKt")

package com.creative.androidfundamentalsbydantech.ui.learning

import com.creative.androidfundamentalsbydantech.ui.navigation.Destination

object LearningContent {
    const val COMPOSE_STATE = "compose-state"
    const val VIEWMODEL_UDF = "viewmodel-udf"
    const val ROOM_PERSISTENCE = "room-persistence"

    val lessons: List<LessonDefinition> = listOf(
        LessonDefinition(
            id = COMPOSE_STATE,
            title = "Compose State",
            moduleTitle = "Compose Foundation",
            summary = "Hoc cach UI nho du lieu, giu state qua recomposition, va day state len tren.",
            concept = "State trong Compose la nguon du lieu lam UI thay doi. `remember` giu gia tri qua recomposition, `rememberSaveable` giu them qua config change.",
            observe = "Khi state nam o component cha, child chi can value va callback. Day la state hoisting, giup UI de test va de tai su dung.",
            challenge = "Tao ten cho Android Learning Journal, thay doi gia tri, roi xoay man hinh de so sanh state nao bi mat.",
            wrapUp = "Dung `rememberSaveable` cho state UI nho can song qua config change; hoist state khi nhieu component cung can doc/sua.",
            minutes = 12,
        ),
        LessonDefinition(
            id = VIEWMODEL_UDF,
            title = "ViewModel + UDF",
            moduleTitle = "App Architecture",
            summary = "Dua state ra ViewModel va xu ly hanh dong theo mot chieu.",
            concept = "ViewModel giu UI state bang StateFlow. Compose doc state, gui event len ViewModel, va khong giu logic business trong composable.",
            observe = "UDF lam dong du lieu ro hon: State di xuong UI, event di len ViewModel, side-effect chay trong scope phu hop.",
            challenge = "Nhap text, encode, quan sat busy state, roi clear history ma khong de UI tu sua truc tiep danh sach.",
            wrapUp = "MVVM hien dai tren Android nen tap trung vao immutable UI state va events hon la tach lop cho co.",
            minutes = 14,
        ),
        LessonDefinition(
            id = ROOM_PERSISTENCE,
            title = "Room Persistence",
            moduleTitle = "Data Layer",
            summary = "Luu Android Learning Journal bang Entity, DAO, Flow va coroutine.",
            concept = "Room bien SQLite thanh API Kotlin co type-safety. DAO tra Flow de UI tu cap nhat khi data thay doi.",
            observe = "Insert/delete/pin note thay doi database, Flow phat danh sach moi, ViewModel expose StateFlow cho Compose.",
            challenge = "Tao mot journal note, pin note do, dong mo lai lesson va kiem tra du lieu van con.",
            wrapUp = "Dung Room cho du lieu co cau truc va can query. Dung DataStore cho preferences/progress nho.",
            minutes = 16,
        ),
        LessonDefinition(
            id = "compose-side-effects",
            title = "Compose Side-effects",
            moduleTitle = "Compose Foundation",
            summary = "Chay cong viec ngoai composition bang effect API dung vong doi.",
            concept = "`LaunchedEffect`, `DisposableEffect`, `produceState` va `snapshotFlow` gan coroutine/lifecycle vao composition.",
            observe = "Key cua effect quyet dinh luc restart. Key sai co the gay request lap, leak listener, hoac stale callback.",
            challenge = "Mo lab va so sanh LaunchedEffect voi rememberCoroutineScope khi trigger tu button.",
            wrapUp = "Dung effect de noi Compose voi the gioi ben ngoai, khong dung de che dau state design kem.",
            labRoute = Destination.ComposeEffects.route,
            minutes = 12,
        ),
        LessonDefinition(
            id = "coroutines-flow",
            title = "Coroutines + Flow",
            moduleTitle = "Concurrency",
            summary = "Hieu async work, cancellation, Flow lanh/nong va StateFlow.",
            concept = "Coroutine chay cong viec bat dong bo co cancellation. Flow mo ta stream du lieu theo thoi gian.",
            observe = "`collectLatest`, debounce, StateFlow va SharedFlow giai quyet cac kieu UI stream khac nhau.",
            challenge = "Mo lab Flow, nhap search nhanh, va quan sat debounce cat bot request trung gian.",
            wrapUp = "Model UI state bang StateFlow; dung Flow operator de bien doi stream truoc khi den UI.",
            labRoute = Destination.Flow.route,
            minutes = 14,
        ),
        LessonDefinition(
            id = "network-paging",
            title = "Network + Paging",
            moduleTitle = "Data Layer",
            summary = "Lay data tu API va tai danh sach theo trang.",
            concept = "Retrofit map HTTP API thanh interface Kotlin. Paging 3 tai tung page de danh sach lon khong bi nang.",
            observe = "Network state can Idle/Loading/Success/Error; paging state can append/prepend/load/error.",
            challenge = "Mo lab Retrofit, tim `android compose`, roi mo lab Paging de xem danh sach tai tiep khi scroll.",
            wrapUp = "Tach API, repository, UI state de loi network khong ro ra khap UI.",
            labRoute = Destination.Network.route,
            minutes = 15,
        ),
        LessonDefinition(
            id = "background-work",
            title = "Background Work",
            moduleTitle = "System Integration",
            summary = "Chay viec tri hoan va dong bo bang WorkManager.",
            concept = "WorkManager phu hop cho deferred work can dam bao chay, ke ca app/process bi dong.",
            observe = "Worker co progress, constraints, retry policy va co the inject dependency bang Hilt.",
            challenge = "Mo lab WorkManager, enqueue job, va quan sat progress thay doi tren UI.",
            wrapUp = "Dung WorkManager cho sync/reminder dang tin cay; khong dung cho viec can chay ngay lap tuc tren foreground.",
            labRoute = Destination.Work.route,
            minutes = 12,
        ),
    )

    val modules: List<LearningModule> =
        lessons.groupBy { it.moduleTitle }.map { (title, moduleLessons) ->
            LearningModule(title = title, lessons = moduleLessons)
        }

    fun lessonById(id: String): LessonDefinition? = lessons.firstOrNull { it.id == id }
}
