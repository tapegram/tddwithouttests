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
        cart.addItem(
            Item(
                id = "item1"
            )
        )

        assert(
            cart.items == listOf(Item(id = "item1"))
        )
    }

    @Test
    fun `can add multiple items to the cart`() {
        val cart = Cart(id="123")
        cart.addItem(
            Item(
                id = "item1"
            )
        )
        cart.addItem(
            Item(
                id = "item2"
            )
        )

        assert(
            cart.items == listOf(
                Item(id = "item1"),
                Item(id = "item2")
            )
        )
    }
}