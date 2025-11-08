'use client';

import ProductControls from "@/components/product/ProductControls";
import ProductPhotos from "@/components/product/ProductPhotos";
import {Product} from "@/types/product";


export default function ProductPageClient({ product }: { product: Product }) {
    return (
        <>
            <main>
                <section
                    style={{
                        width: '100%',
                        backgroundColor: 'white',
                        height: '200px',
                    }}
                ></section>
                <section className="flex justify-between items-start w-full max-w-7xl mx-auto px-4 py-8 gap-8 font-logo mb-60">
                    <ProductPhotos product={product}/>

                    <ProductControls product={product}/>
                </section>
            </main>
        </>
    );
}
