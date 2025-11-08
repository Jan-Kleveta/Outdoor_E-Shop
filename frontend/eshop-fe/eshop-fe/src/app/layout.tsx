import type { Metadata } from 'next';
import './globals.css';
import QueryProvider from './QueryProvider';
import Header from "@/components/header/Header";
import Footer from "@/components/footer/Footer";


export const metadata: Metadata = {
    title: 'Outdoorio',
    description: 'Premium ultralight wear made in Czech Republic',
};

export default function RootLayout({
                                       children,
                                   }: {
    children: React.ReactNode;
}) {
    return (
        <html lang="en">
        <head>
            <link
                href="https://fonts.googleapis.com/css2?family=Sorts+Mill+Goudy&display=swap"
                rel="stylesheet"
            />
            <title>Outdoorio</title>
        </head>
        <body>
        <QueryProvider>
            <Header/>
            <main>{children}</main>
            <Footer/>
        </QueryProvider>
        </body>
        </html>
    );
}
