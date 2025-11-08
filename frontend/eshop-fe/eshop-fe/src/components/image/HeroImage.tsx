type HeroProps = {
    imageUrl: string;
    height: `${number}px` | `${number}rem` | `${number}%`;
    headline?: string;
    mt?: string;
    altText?: string;
    loading?: "lazy" | "eager";
};

export default function HeroImage({
                                      imageUrl,
                                      height,
                                      headline,
                                      mt,
                                      altText = "Hero image",
                                      loading = "lazy"
                                  }: HeroProps) {
    return (
        <section
            className={`w-full flex items-center justify-center ${mt ?? ''}`}
            style={{ height }}
        >
            <img
                src={imageUrl}
                alt={altText}
                loading={loading}
                className="object-cover w-full h-full"
            />
            {headline && (
                <h1 className="absolute text-3xl font-logo drop-shadow-lg text-white">
                    {headline}
                </h1>
            )}
        </section>
    );
}
