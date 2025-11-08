'use client';

import {useCartStore} from "@/store/cartStore";
import {useCreateCheckoutSessionMutation} from "@/app/hooks/useCreateCheckoutSessionMutation";


export default function CartPage() {
    const { items, clearCart } = useCartStore();
    const { mutate, isPending, isSuccess, isError, error } = useCreateCheckoutSessionMutation();

    const handleCheckout = () => {
        mutate(undefined, {
            onSuccess: (data) => {
                if (data?.url) {
                    window.location.href = data.url;
                }
                clearCart();
            },
        });
    };

    return (
        <>
            <div className="max-w-4xl mx-auto py-8 space-y-4">
                <h1 className="text-2xl font-bold mb-4">Shopping Cart</h1>

                {items.length === 0 ? (
                    <p>Your cart is empty.</p>
                ) : (
                    <>
                        <ul className="space-y-2">
                            {items.map(item => (
                                <li key={item.productWithSizeId} className="border p-4 rounded">
                                    <p><strong>Product:</strong> {item.name}</p>
                                    <p><strong>Quantity:</strong> {item.quantity}</p>
                                    <p>
                                        <strong>Price:</strong>{" "}
                                        {item.price}{" "}
                                        {item.currency}
                                    </p>
                                    <p><strong>Size:</strong> {item.size}</p>
                                </li>
                            ))}
                        </ul>

                        <div className="mt-6 flex flex-col sm:flex-row gap-4">
                            <button
                                onClick={clearCart}
                                className="px-5 py-2.5 bg-red-600 text-white rounded-md font-medium hover:bg-red-700 transition disabled:opacity-50 disabled:cursor-not-allowed"
                            >
                                Clear Cart
                            </button>

                            <button
                                onClick={handleCheckout}
                                disabled={isPending}
                                className="px-5 py-2.5 bg-black text-white rounded-md font-medium hover:bg-gray-800 transition disabled:opacity-50 disabled:cursor-not-allowed"
                            >
                                {isPending ? 'Creating order…' : 'Create order'}
                            </button>
                            {isError && <p style={{color: 'red'}}>{(error as Error).message}</p>}
                            {isSuccess && <p style={{color: 'green'}}>Stripe session created!</p>}
                        </div>
                    </>
                )}
            </div>
        </>
    );
}
