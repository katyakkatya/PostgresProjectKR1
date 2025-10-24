package models

data class OrderOptionsModel(
  val order: Order = Order.UNSET
)

enum class Order {
  ASC,
  DESC,
  UNSET
}