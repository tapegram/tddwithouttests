import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `create a cart`() {
        val cart = Cart(id="123")
        assert(
            cart.id == "123"
        )
    }

    @Test
    fun `can add an item to a cart`() {
        val cart = Cart(id="123")
        val item = Item(
            id = "item1",
            price =1
        )
        cart.addItem(item)

        assert(
            cart.items == listOf(item)
        )
    }

    @Test
    fun `can add multiple items to the cart`() {
        val cart = Cart(id="123")
        val item1 = Item(
            id = "item1",
            price =1
        )
        val item2 = Item(
            id = "item2",
            price =1
        )
        cart.addItem(item1)
        cart.addItem(item2)

        assert(
            cart.items == listOf(
                item1,
                item2
            )
        )
    }
}