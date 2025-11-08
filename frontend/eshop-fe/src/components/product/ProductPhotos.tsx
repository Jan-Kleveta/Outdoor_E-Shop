import { Product, PhotoType } from '@/types/product';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
import 'swiper/css';
import 'swiper/css/navigation';

type ProductPhotosProps = {
    product: Product;
};

export default function ProductPhotos({ product }: ProductPhotosProps) {

    const mainPhoto = product.productPhotos.find((p) => p.photoType === PhotoType.Main);
    const modelPhotos = product.productPhotos.filter((p) => p.photoType === PhotoType.Model);
    const combinedPhotos = mainPhoto ? [mainPhoto, ...modelPhotos] : modelPhotos;

    return (
        <div className="flex justify-end items-end w-full pt-10">
            <div className="w-full max-w-[700px]">
                <Swiper
                    modules={[Navigation]}
                    spaceBetween={10}
                    navigation
                    className="w-full"
                >
                    {combinedPhotos.map((photo) => (
                        <SwiperSlide key={photo.id}>
                            <div className="flex justify-center items-center min-h-[600px]">
                                <img
                                    src={`http://localhost:8088${photo.path}`}
                                    alt={photo.description}
                                    className="object-contain max-h-[600px] w-full h-full max-w-full transition-opacity duration-300 ease-in-out"
                                />
                            </div>
                        </SwiperSlide>
                    ))}
                </Swiper>
            </div>
        </div>
    );
}
