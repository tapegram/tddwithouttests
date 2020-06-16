import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `create a cart`() {
        val cart = Cart(id="123")
        assert(
            cart.id == "123"
        )
    }
}