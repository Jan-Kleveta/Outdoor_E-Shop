import { useQuery } from '@tanstack/react-query';
import { useAuthStore } from '@/store/authStore';

export interface PhoneNumber {
    localNumber: string;
}

export interface Address {
    country: string;
    city: string;
    addressLine1: string;
    addressLine2: string;
    zipCode: string;
    name: string;
    phoneNumber?: PhoneNumber;
}

export interface ShippingMethod {
    price: number;
    trackingNumber: string | null;
    carrier: string;
}

export interface ProductInstance {
    productName: string;
    orderedQuantity: number;
    pricePerUnit: number;
    currency: string;
}

export interface OrderResponse {
    timestamp: string | null;
    email: string;
    orderNumber: string;
    totalPrice: number;
    currency: string;
    status: string;
    billingAddressResponse: Address;
    shippingAddressResponse: Address;
    shippingMethodResponse: ShippingMethod;
    productInstanceList: ProductInstance[];
}

export const useUserOrdersQuery = () => {
    const token = useAuthStore((s) => s.token);

    return useQuery<OrderResponse[]>({
        queryKey: ['userOrders'],
        queryFn: async () => {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/order/v1/public/orders/user`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            if (!res.ok) {
                const error = await res.text();
                throw new Error(error || 'Failed to fetch user orders');
            }

            return res.json();
        },
    });
};
