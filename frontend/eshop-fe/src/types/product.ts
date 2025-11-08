export type Product = {
    id: number;
    name: string;
    description: string;
    prices: { price: number; currency: string }[];
    productPhotos: { id: number; path: string; description: string; photoType: PhotoType}[];
    productsWithSize: { id: number; size: string; stockQuantity: number; weight: number }[];
};

export enum PhotoType {
    Main = 'MAIN',
    Model = 'MODEL',
    Detail = 'DETAIL',
}

export type CartItem = {
    productWithSizeId: number;
    orderedQuantity: number;
    currency: string;
    price: number;
    product: Product;
};
