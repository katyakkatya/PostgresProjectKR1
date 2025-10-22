package models

data class FormattingOptionsModel(
  val heightTransformation: HeightTransformation = HeightTransformation.UNSET,
  val showShort: Boolean = false,
  val displayId: Boolean = false,
  val displayFullStatus: Boolean = false,
)

enum class HeightTransformation {
  UPPERCASE, LOWERCASE, UNSET
}
