import {useMutation} from '@tanstack/react-query';
import {useAuthStore} from '@/store/authStore';
import {useCartStore} from "@/store/cartStore";

interface OrderItem {
    productId: number;
    productWithSizeId: number;
    orderedQuantity: number;
    currency: string;
}

interface OrderPayload {
    items: OrderItem[];
}

export const useCreateCheckoutSessionMutation = () => {
    const token = useAuthStore((s) => s.token);
    const cartItems = useCartStore((s) => s.items);

    return useMutation({
        mutationFn: async () => {
            const orderPayload: OrderPayload = {
                items: cartItems.map((item) => ({
                    productId: item.productId,
                    productWithSizeId: item.productWithSizeId,
                    orderedQuantity: item.quantity,
                    currency: item.currency,
                })),
            };

            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/stock/v1/public/checkout`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(orderPayload),
            });

            if (!res.ok) {
                const errorResponse = await res.json().catch(() => null);
                throw new Error(errorResponse?.message || 'There was an error while creating the order');
            }


            return await res.json();
        },
    });
};
