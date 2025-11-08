'use client';

import { useState, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { useRegisterMutation } from '@/app/hooks/useRegisterMutation';
import Link from "next/link";
import { Eye, EyeOff, Check, X } from 'lucide-react';

export default function RegisterForm() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordCheck, setPasswordCheck] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [showPasswordCheck, setShowPasswordCheck] = useState(false);

    const router = useRouter();
    const { mutate: register, isPending, isError, error } = useRegisterMutation();

    const passwordRequirements = {
        length: password.length >= 9,
        uppercase: /[A-Z]/.test(password),
        specialChar: /[,!@#$&*]/.test(password),
    };

    const validatePasswords = () => {
        if (password !== passwordCheck) {
            setPasswordError("Passwords do not match");
            return false;
        }
        if (!passwordRequirements.length || !passwordRequirements.uppercase || !passwordRequirements.specialChar) {
            setPasswordError("Password must meet all requirements");
            return false;
        }
        setPasswordError("");
        return true;
    };

    const handleSubmit = useCallback((e: React.FormEvent) => {
        e.preventDefault();

        if (!validatePasswords()) return;

        register(
            { email, password, passwordCheck },
            {
                onSuccess: () => {
                    router.push('/user/login');
                },
            }
        );
    }, [email, password, passwordCheck, register, router]);

    return (
        <form onSubmit={handleSubmit} className="max-w-md mx-auto space-y-4 p-6 border rounded">
            <h2 className="text-xl font-bold text-center">Register</h2>

            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
                required
                disabled={isPending}
                className="w-full border p-2 rounded"
            />

            <div className="relative">
                <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                    disabled={isPending}
                    className={`w-full border p-2 rounded ${passwordError ? 'border-red-500' : ''}`}
                />
                <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-black"
                >
                    {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                </button>
            </div>

            <div className="text-sm space-y-1">
                <div className="flex items-center gap-2">
                    {passwordRequirements.length ? (
                        <Check className="text-green-600" />
                    ) : (
                        <X className="text-red-500" />
                    )}
                    <p className={passwordRequirements.length ? 'text-green-600' : 'text-red-500'}>
                        At least 9 characters
                    </p>
                </div>

                <div className="flex items-center gap-2">
                    {passwordRequirements.uppercase ? (
                        <Check className="text-green-600" />
                    ) : (
                        <X className="text-red-500" />
                    )}
                    <p className={passwordRequirements.uppercase ? 'text-green-600' : 'text-red-500'}>
                        At least one uppercase letter
                    </p>
                </div>

                <div className="flex items-center gap-2">
                    {passwordRequirements.specialChar ? (
                        <Check className="text-green-600" />
                    ) : (
                        <X className="text-red-500" />
                    )}
                    <p className={passwordRequirements.specialChar ? 'text-green-600' : 'text-red-500'}>
                        At least one special character (,!@#$&*)
                    </p>
                </div>
            </div>

            <div className="relative">
                <input
                    type={showPasswordCheck ? "text" : "password"}
                    value={passwordCheck}
                    onChange={(e) => setPasswordCheck(e.target.value)}
                    placeholder="Confirm Password"
                    required
                    disabled={isPending}
                    className={`w-full border p-2 rounded ${passwordError ? 'border-red-500' : ''}`}
                />
                <button
                    type="button"
                    onClick={() => setShowPasswordCheck(!showPasswordCheck)}
                    className="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-black"
                >
                    {showPasswordCheck ? <EyeOff size={20} /> : <Eye size={20} />}
                </button>
            </div>

            {passwordError && (
                <p className="text-red-500 text-sm">{passwordError}</p>
            )}

            {isError && (
                <p className="text-red-500 text-sm">{(error as Error)?.message || 'Something went wrong'}</p>
            )}

            <button
                type="submit"
                className="w-full bg-black text-white p-2 rounded disabled:opacity-50 flex items-center justify-center gap-2"
                disabled={isPending}
            >
                {isPending ? (
                    <>
                        <span className="loader animate-spin w-4 h-4 border-2 border-white border-t-transparent rounded-full" />
                        Registering...
                    </>
                ) : (
                    'Register'
                )}
            </button>

            <Link
                href="/user/login"
                className="block w-full bg-white text-black border border-black p-2 rounded text-center hover:bg-gray-100 transition"
            >
                Login
            </Link>
        </form>
    );
}
