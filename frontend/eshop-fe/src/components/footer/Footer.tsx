import Link from "next/link";

export default function Footer() {
    return (
        <footer className="">
            <div
                className="max-w-7xl mx-auto px-4 py-8 flex flex-col md:flex-row items-center justify-center gap-x-64 gap-y-6 text-base mt-12">
                <div>
                    <div><Link href="/policies/shipping">Delivery and payment</Link></div>
                    <div><Link href="/policies/returns">Returns and exchanges</Link></div>
                    <div><Link href="/policies/terms">Terms and conditions</Link></div>
                </div>
                <div>
                    <div><Link href="/policies/warranty">Warranty</Link></div>
                    <div><Link href="/policies/gdpr">GDPR</Link></div>
                    <div><Link href="/policies/cookies">Cookies</Link></div>
                </div>
                <div>
                    <strong>Support:</strong>
                    <p>help@firm.cz</p>
                </div>
            </div>
        </footer>
    );
}
