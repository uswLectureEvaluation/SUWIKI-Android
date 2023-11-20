@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  id("suwiki.android.library")
  id("suwiki.android.hilt")
  alias(libs.plugins.ksp)
  alias(libs.plugins.protobuf)
}

ksp {
  arg("room.schemaLocation", "$rootDir/schemas")
}

android {
  namespace = "com.suwiki.core.database"
}

protobuf {
  protoc {
    artifact = libs.protobuf.protoc.get().toString()
  }
  generateProtoTasks {
    all().forEach { task ->
      task.builtins {
        register("java") {
          option("lite")
        }
      }
    }
  }
}

dependencies {
  ksp(libs.room.compiler)
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)

  implementation(libs.bundles.coroutine)
  implementation(libs.androidx.datastore.core)
  implementation(libs.androidx.datastore.preferences)
  implementation(libs.protobuf.kotlin.lite)
}
