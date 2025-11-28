package models

data class OrderOptionsModel(
  val orderBy: OrderBy = OrderBy.UNSET,
  val order: Order = Order.ASC
)

enum class Order {
  ASC,
  DESC,
}

enum class OrderBy {
  DATE,
  TITLE,
  UNSET
}