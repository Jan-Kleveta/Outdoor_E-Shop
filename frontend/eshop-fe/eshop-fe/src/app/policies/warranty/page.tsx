'use client';

import { useState } from 'react';
import WarrantyEN from '@/components/footer/warranty/WarrantyEN';
import Orders from "@/components/header/user/Orders";
import WarrantyCZ from "@/components/footer/warranty/WarrantyCZ";

export default function WarrantyPage() {
    const [lang, setLang] = useState<'cz' | 'en'>('en');

    return (
        <div className="min-h-screen bg-white text-black p-4">
            <section
                style={{
                    width: '100%',
                    backgroundColor: 'white',
                    height: '200px',
                }}
            ></section>
            <div className="flex justify-center mb-6 space-x-6">
                <button
                    onClick={() => setLang('en')}
                    className={`text-lg transition cursor-pointer ${
                        lang === 'en' ? 'underline' : 'text-gray-600 hover:text-black'
                    }`}
                >
                    English
                </button>
                <button
                    onClick={() => setLang('cz')}
                    className={`text-lg transition cursor-pointer ${
                        lang === 'cz' ? 'underline' : 'text-gray-600 hover:text-black'
                    }`}
                >
                    Česky
                </button>
            </div>


            <div className="max-w-4xl mx-auto">
                {lang === 'en' ? <WarrantyEN/> : <WarrantyCZ/>}
            </div>
        </div>
    );
}
