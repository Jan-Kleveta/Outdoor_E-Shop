import { create } from 'zustand';
import { persist } from 'zustand/middleware';

type CartItem = {
    productWithSizeId: number;
    productId: number;
    size: string;
    quantity: number;
    name: string;
    price: number;
    currency: string;
    image: string;
};

interface CartState {
    items: CartItem[];
    addToCart: (item: CartItem) => void;
    removeFromCart: (productWithSizeId: number) => void;
    clearCart: () => void;
}

export const useCartStore = create<CartState>()(
    persist(
        (set, get) => ({
            items: [],

            addToCart: (newItem) => {
                const existingItem = get().items.find(
                    (item) => item.productWithSizeId === newItem.productWithSizeId
                );

                if (existingItem) {
                    set({
                        items: get().items.map((item) =>
                            item.productWithSizeId === newItem.productWithSizeId
                                ? { ...item, quantity: item.quantity + newItem.quantity }
                                : item
                        ),
                    });
                } else {
                    set({ items: [...get().items, newItem] });
                }
            },

            removeFromCart: (productWithSizeId) => {
                set({
                    items: get().items.filter(item => item.productWithSizeId !== productWithSizeId),
                });
            },

            clearCart: () => set({ items: [] }),
        }),
        {
            name: 'cart-storage',
        }
    )
);
