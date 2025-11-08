import Learn from "@/components/header/Learn";
import GdprEN from "@/components/footer/gdpr/GdprEN";
import GdprCZ from "@/components/footer/gdpr/GdprCZ";

export default function LearnPage() {
    return (
        <div className="min-h-screen bg-white text-black p-4">
            <section
                style={{
                    width: '100%',
                    backgroundColor: 'white',
                    height: '200px',
                }}
            ></section>
            <div className="max-w-4xl mx-auto">
                <Learn/>
            </div>
        </div>
    );
}