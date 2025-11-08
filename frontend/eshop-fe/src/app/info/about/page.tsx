import About from "@/components/header/About";

export default function AboutPage() {
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
                <About/>
            </div>
        </div>
    );
}