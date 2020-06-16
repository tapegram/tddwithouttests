package ooptdd

import ooptdd.Cart
import ooptdd.Item
import ooptdd.Payment
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `create a cart`() {
        val cart = Cart(id = "123")
        assert(
            cart.id == "123"
        )
    }

    @Test
    fun `can add an item to a cart`() {
        val cart = Cart(id = "123")
        val item = Item(
            id = "item1",
            price = 1
        )
        cart.addItem(item)

        assert(
            cart.items == listOf(item)
        )
    }

    @Test
    fun `can add multiple items to the cart`() {
        val cart = Cart(id = "123")
        val item1 = Item(
            id = "item1",
            price = 1
        )
        val item2 = Item(
            id = "item2",
            price = 1
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

    @Test
    fun `cant checkout with empty cart`() {
        invoking {
            Cart(id = "123").checkout(emptyList())
        } shouldThrow RuntimeException::class
    }

    @Test
    fun `cant checkout with payments less than total`() {
        val cart = Cart(id = "123")
        val item = Item(
            id = "item1",
            price = 2
        )
        cart.addItem(item)

        invoking {
            cart.checkout(payments=listOf(Payment(amount = 1)))
        } shouldThrow RuntimeException::class
    }

    @Test
    fun `cant checkout with payments more than total`() {
        val cart = Cart(id = "123")
        val item = Item(
            id = "item1",
            price = 2
        )
        cart.addItem(item)

        invoking {
            cart.checkout(payments=listOf(Payment(amount = 3)))
        } shouldThrow RuntimeException::class
    }

    @Test
    fun `can checkout when payment matches cart total`() {
        val cart = Cart(id = "123")
        val item = Item(
            id = "item1",
            price = 2
        )
        cart.addItem(item)

        val result = cart.checkout(payments=listOf(Payment(amount = 2)))
        result shouldBeEqualTo true
    }

    @Test
    fun `checkout is idempotent`() {
        val cart = Cart(id = "123")
        val item = Item(
            id = "item1",
            price = 2
        )
        cart.addItem(item)

        cart.checkout(payments=listOf(Payment(amount = 2)))

        invoking {
            cart.checkout(payments=listOf(Payment(amount = 2)))
        } shouldThrow RuntimeException::class
    }
}