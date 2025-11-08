import { useQueries } from '@tanstack/react-query';

export const useProductsQuery = (ids: number[]) => {
    return useQueries({
        queries: ids.map((id) => ({
            queryKey: ['product', id],
            queryFn: async () => {
                const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/stock/v1/product/${id}`);
                if (!res.ok) throw new Error('Product not found');
                return res.json();
            },
        })),
    });
};
