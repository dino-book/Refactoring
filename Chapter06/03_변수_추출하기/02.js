function price(order) {
    // 가격(price) = 기본 가격 - 수량 할인 + 배송비
    const basePrice = order.quantity * order.itemPrice 
    return basePrice -
    Math.max(0, order.quantity - 500) * order.itemPrice * 0.05 +
    Math.min(basePrice * 0.1, 100)
}